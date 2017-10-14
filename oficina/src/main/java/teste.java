
import org.springframework.web.client.RestTemplate;

import tattool.domain.model.Cep;

public class teste {

	public static void main(String[] args) {
		RestTemplate rest = new RestTemplate();
		Cep cep = rest.getForObject("http://viacep.com.br/ws/19807822/json", Cep.class);
		System.out.println(cep.toString());
	}
}
