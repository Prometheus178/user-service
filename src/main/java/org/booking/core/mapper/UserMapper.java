package org.booking.core.mapper;

import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.UserRequest;
import org.booking.core.domain.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


	User toEntity(UserRequest userRequest);

	UserResponse toResponse(User user);

}
