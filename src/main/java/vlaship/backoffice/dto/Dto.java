package vlaship.backoffice.dto;

import org.springframework.lang.NonNull;

import java.io.Serializable;

public interface Dto extends Serializable {

	@NonNull
	Integer getId();

}