package com.feeney.daniel.notify.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.feeney.daniel.notify.config.FcmSettings;
import com.feeney.daniel.notify.dto.PublicacaoDTO;
import com.feeney.daniel.notify.dto.TagDTO;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.model.PublicacaoTag;
import com.feeney.daniel.notify.model.Tag;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.services.FiltrosService;
import com.feeney.daniel.notify.services.MessageService;
import com.feeney.daniel.notify.services.PublicacaoService;
import com.feeney.daniel.notify.services.PublicacaoTagService;
import com.feeney.daniel.notify.services.StorageService;
import com.feeney.daniel.notify.services.TagService;
import com.feeney.daniel.notify.services.UsuarioService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.common.io.CharStreams;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

@RestController
@CrossOrigin
@RequestMapping("/publicacao")
public class PublicacaoResource {

	@Autowired
	public PublicacaoService publicacaoService;
	@Autowired
	public UsuarioService usuarioService;
	@Autowired
	public PublicacaoTagService publicacaoTagService;

	@Autowired
	public TagService tagService;

	@Autowired
	public StorageService storageService;
	
	@Autowired
	public MessageService messageService;
	
	@Autowired
	public FiltrosService filtrosService;

	@PreAuthorize("hasAnyRole('ROLE_PUBLICACAO')")
	@GetMapping("/preferencias/")
	public ResponseEntity<?> buscarTodos(
			@RequestParam String cpf,
			@RequestParam(value = "page") Integer page,
			@RequestParam(value = "linesPerPage") Integer linesPerPage,
			@RequestParam(value = "filtros") List<Long> filtros) {
		List<Publicacao> list = publicacaoService.buscarTodosPelaPreferenciaDoUsuario(filtros, page, linesPerPage);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PreAuthorize("hasAnyRole('ROLE_PUBLICACAO')")
	@GetMapping("/{idPublicacao}")
	public ResponseEntity<?> buscar(@PathVariable Long idPublicacao) {
		Optional<Publicacao> optPublicacao = publicacaoService.buscar(idPublicacao);
		if (optPublicacao.isPresent()) {
			PublicacaoDTO publicacaoDTO = new PublicacaoDTO(optPublicacao.get());
			publicacaoDTO.setColTagDTO(tagService.listarTodosTagDTOPublicacao(idPublicacao));
			return ResponseEntity.status(HttpStatus.OK).body(publicacaoDTO);
		} else
			return ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_POST_PUBLICACAO')")
	@PostMapping()
	public ResponseEntity<?> salvar(
			@RequestParam(value = "id",  required = false) Long idPublicacao,
			@RequestParam(value = "titulo") String titulo,
			@RequestParam(value = "subTitulo", required = false) String subTitulo,
			@RequestParam(value = "descricao") String descricao, @RequestParam(value = "cpf") String cpf,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "listaTag") List<String> listaTag) {

		Publicacao publicacao = new Publicacao(idPublicacao, titulo, subTitulo, descricao, cpf);
		Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(cpf);
		if (optUsuario.isPresent()) {
			publicacao.setUsuarioPublicacao(optUsuario.get());
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
		}
		publicacaoService.salvar(publicacao);
		
		Collection<Tag> tags = new ArrayList<>();

		if (idPublicacao == null) {
			for (String tagId : listaTag) {
				Optional<Tag> tagOpt = tagService.buscar(Long.parseLong(tagId));
				if (tagOpt.isPresent()) {
					tags.add(tagOpt.get());
					PublicacaoTag publicacaoTag = new PublicacaoTag(publicacao, tagOpt.get());
					publicacaoTagService.salvar(publicacaoTag);
				}
			}
		} else {
			tags = tagService.listarTagDePublicacao(idPublicacao);

			for (String tagId : listaTag) {
				Optional<Tag> tagOpt = tagService.buscar(Long.parseLong(tagId));
				if (tagOpt.isPresent()) {
					Tag tag = tagOpt.get();
					if (tags.contains(tag)) {
						tags.remove(tag);
					} else {
						PublicacaoTag publicacaoTag = new PublicacaoTag(publicacao, tag);
						publicacaoTagService.salvar(publicacaoTag);
					}
				}
			}

			for (Tag tag : tags) {
				Optional<PublicacaoTag> publicacaoTagOpt = publicacaoTagService
						.buscarPorPublicacaoETag(publicacao.getId(), tag.getId());
				if (publicacaoTagOpt.isPresent()) {
					publicacaoTagService.remover(publicacaoTagOpt.get());
				}
			}
		}

		if(file != null) {
			try {
				storageService.store(file, publicacao.getId().toString());
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
			}
		}
		
		
		Collection<String> colTokens = filtrosService.buscarTokenDeUsuarioPorTag(tags);
		
		for(String token : colTokens) {
			messageService.sendPushMessages(token, String.valueOf(publicacao.getId()), publicacao.getTitulo(), publicacao.getDescricao());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(publicacao);
	}

	@PreAuthorize("hasAnyRole('ROLE_DELETE_PUBLICACAO')")
	@PostMapping("/delete")
	public ResponseEntity<?> deletar(@PathParam(value = "idPublicacao") String idPublicacao) {
		Optional<Publicacao> optPublicacao = publicacaoService.buscar(Long.parseLong(idPublicacao));
		if (optPublicacao.isPresent()) {
			publicacaoService.remover(Long.parseLong(idPublicacao));
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}
	
	@PreAuthorize("hasAnyRole('ROLE_PUBLICACAO')")
	@GetMapping("/imagem/{idPublicacao}")
	  public ResponseEntity<?> getFile(@PathVariable Long idPublicacao) {
	    try {
	    	Resource file = storageService.loadFile("file" + idPublicacao + ".jpg");
    	    
			return ResponseEntity.ok()
			    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
			    .body(file);
	    }
	    catch(Exception e) {
	    	return ResponseEntity.ok().build();
	    }
	  }

}
