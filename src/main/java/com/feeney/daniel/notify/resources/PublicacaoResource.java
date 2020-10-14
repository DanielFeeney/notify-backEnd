package com.feeney.daniel.notify.resources;

import java.io.FileInputStream;
import java.io.InputStream;
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

import com.feeney.daniel.notify.config.FcmSettings;
import com.feeney.daniel.notify.dto.PublicacaoDTO;
import com.feeney.daniel.notify.dto.TagDTO;
import com.feeney.daniel.notify.model.Publicacao;
import com.feeney.daniel.notify.model.PublicacaoTag;
import com.feeney.daniel.notify.model.Tag;
import com.feeney.daniel.notify.model.Usuario;
import com.feeney.daniel.notify.services.PublicacaoService;
import com.feeney.daniel.notify.services.PublicacaoTagService;
import com.feeney.daniel.notify.services.TagService;
import com.feeney.daniel.notify.services.UsuarioService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

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
	public FcmSettings settings;

	@PreAuthorize("hasAnyRole('ROLE_PUBLICACAO')")
	@GetMapping("/preferencias/{cpf}")
	public ResponseEntity<?> buscarTodos(@PathVariable String cpf,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage) {
		List<Publicacao> list = publicacaoService.buscarTodosPelaPreferenciaDoUsuario(cpf, page, linesPerPage);
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
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody PublicacaoDTO publicacaoDTO) {
		Publicacao publicacao = new Publicacao(publicacaoDTO);
		Optional<Usuario> optUsuario = usuarioService.buscarPeloCpf(publicacaoDTO.getCpfUsuario());
		if (optUsuario.isPresent()) {
			publicacao.setUsuarioPublicacao(optUsuario.get());
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
		}
		publicacaoService.salvar(publicacao);

		if (publicacaoDTO.getId() == null) {
			for (TagDTO tagDTO : publicacaoDTO.getColTagDTO()) {
				if (tagDTO.getSelecionado()) {
					Optional<Tag> tagOpt = tagService.buscar(tagDTO.getId());
					if (tagOpt.isPresent()) {
						PublicacaoTag publicacaoTag = new PublicacaoTag(publicacao, tagOpt.get());
						publicacaoTagService.salvar(publicacaoTag);
					}
				}
			}
		} else {
			Collection<Tag> tags = tagService.listarTagDePublicacao(publicacaoDTO.getId());

			for (TagDTO tagDTO : publicacaoDTO.getColTagDTO()) {
				if(tagDTO.getSelecionado()) {
					Optional<Tag> tagOpt = tagService.buscar(tagDTO.getId());
					if (tagOpt.isPresent()) {
						Tag tag = tagOpt.get();
						if (tags.contains(tag)) {
							tags.remove(tag);
						}
						else {
							PublicacaoTag publicacaoTag = new PublicacaoTag(publicacao, tag);
							publicacaoTagService.salvar(publicacaoTag);
						}
					}
				}
			}
			
			for(Tag tag : tags) {
				Optional<PublicacaoTag> publicacaoTagOpt = publicacaoTagService.buscarPorPublicacaoETag(publicacao.getId(), tag.getId());
				if(publicacaoTagOpt.isPresent()) {
					publicacaoTagService.remover(publicacaoTagOpt.get());
				}
			}
		}
		

		try {
			FileInputStream serviceAccount =
					  new FileInputStream("path/to/serviceAccountKey.json");
					
					FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .setDatabaseUrl("https://notify-pushnotification.firebaseio.com")
					  .build();
					
					FirebaseApp.initializeApp(options);


					FirebaseApp fireApp = FirebaseApp.initializeApp(options);
					
					StorageClient storageClient = StorageClient.getInstance(fireApp);
					        InputStream testFile = new FileInputStream("YOUR FILE PATH");
					        String blobString = "NEW_FOLDER/" + "FILE_NAME.EXT";  
			        storageClient.bucket().create(blobString, testFile , Bucket.BlobWriteOption.userProject("YOUR PROJECT ID"));
		}
		catch(Exception e) {
			
		}

		

		return ResponseEntity.status(HttpStatus.OK).body(publicacao);
	}

	@PreAuthorize("hasAnyRole('ROLE_PUBLICACAO')")
	@PostMapping("/delete")
	public ResponseEntity<?> deletar(@PathParam(value = "idPublicacao") String idPublicacao) {
		Optional<Publicacao> optPublicacao = publicacaoService.buscar(Long.parseLong(idPublicacao));
		if (optPublicacao.isPresent()) {
			publicacaoService.remover(Long.parseLong(idPublicacao));
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}

}
