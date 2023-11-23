package vlaship.backoffice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vlaship.backoffice.dto.LoginRequest;
import vlaship.backoffice.dto.LoginResponse;
import vlaship.backoffice.dto.SignupRequest;
import vlaship.backoffice.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "username", target = "name")
    @Mapping(source = "password", target = "password")
    User convert(LoginRequest req);

    @Mapping(source = "username", target = "name")
    @Mapping(source = "password", target = "password")
    User convert(SignupRequest req);

    @Mapping(target = "token")
    LoginResponse convert(String token);

}
