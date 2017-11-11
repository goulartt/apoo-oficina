package tattool.rest.consume;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tatool.util.Constantes;
import tattool.domain.model.Art;
import tattool.domain.model.Tag;
import tattool.service.ArtService;


public class ArtRest {
	
	private RestTemplate rest = new RestTemplate();
	private ArtService service = new ArtService();
	
	
	public void save(File file, Art art, List<Tag> tags) throws IOException {
		for(Tag tag : tags) {
			art.getTags().add(tag);
		}
		art.setImage(service.convertFileToByte(file));
		try {
			rest.postForObject(Constantes.Api.URL_DEV+"/art", art, Art.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveArt(Art art) {
		try {
			rest.postForObject(Constantes.Api.URL_DEV+"/art", art, Art.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public HttpStatus deleteImage(Integer id) {
		String url = Constantes.Api.URL_DEV+"/art/img/{id}";
		
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id", id);
	     
	    RestTemplate restTemplate = new RestTemplate();
	    try {
	    	restTemplate.delete ( url,  params );
	    	return HttpStatus.OK;
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	}
	
	public Art[] findArt(String tag) {
		String url = Constantes.Api.URL_DEV+"/art";

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("tag", tag);
		return rest.getForObject(builder.buildAndExpand().toUri(), Art[].class);  
	}
	
	/*public BufferedImage findImg(String key) throws IOException {
		String url = Constantes.Api.URL_DEV+"/art/img";

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("key", key);
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		File file = rest.getForObject(builder.buildAndExpand().toUri(), File.class); 
		BufferedImage image = ImageIO.read(file);
		return image;
	}*/
	

	
	public Art[] findAll() {
		String url = Constantes.Api.URL_DEV+"/art/all";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url,  Art[].class); 
	
	}

	




	
	
	
	
	
}
