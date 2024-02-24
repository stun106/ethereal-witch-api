package com.ethereal.witch.web.controllers;

import com.ethereal.witch.models.user.UserClient;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ethereal.witch.service.UserService;
import com.ethereal.witch.service.exception.EntityNotfoundException;
import com.ethereal.witch.web.dto.UserCreateDto;
import com.ethereal.witch.web.dto.UserPasswordDto;
import com.ethereal.witch.web.dto.UserResponseDto;
import com.ethereal.witch.web.dto.mapper.UserMapper;
import com.ethereal.witch.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User", description = "Contains all the information related to registering, editing and reading a user.")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Operation(summary = "Creates a new resource.", description = "Resource for created a new user.",
            responses = {@ApiResponse(responseCode = "201", description = "Resourse created sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409",description = "User email allready registed of system.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",description = "Resources not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid UserCreateDto userDto) {
        var cryptPassword = BCrypt.withDefaults().hashToString(12, userDto.getPassword().toCharArray());
        userDto.setPassword(cryptPassword);
        UserClient user = userService.saveUser(UserMapper.toUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @Operation(summary = "Returns all users.", description = "Resource for returns all users.",
            responses = {@ApiResponse(responseCode = "201", description = "User returns sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",description = "User not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })

    @GetMapping("/all/auth")
    public ResponseEntity<Object> index(HttpServletRequest request) {
        List<UserClient> allUser = userService.findAllUser(request);
        if (allUser.isEmpty()) {
            throw new EntityNotfoundException("Users not found.");
        }
        return ResponseEntity.status(201).body(UserMapper.toListDto(allUser));
    }
    @Operation(summary = "Returns resource by id.", description = "Resource for returns user by id.",
            responses = {@ApiResponse(responseCode = "201", description = "User returns successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "User not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })

    @GetMapping("/single/{id}")
    public ResponseEntity<Object> findId(@PathVariable Long id) {
        UserClient userId = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(userId));
    }
    @Operation(summary = "Returns resource ilike.", description = "Resource for returns users ilike.",
            responses = {@ApiResponse(responseCode = "201", description = "User returns successfully.",
                    content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
                    @ApiResponse(responseCode = "404",description = "User not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })

    @GetMapping("/")
    public ResponseEntity<Object> findByNameIlike(@RequestParam("user") String user) {
        var userIlike = userService.findiLike(user);

        if (userIlike.isEmpty()) {
            throw new EntityNotfoundException(String.format("User not found."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userIlike);
    }
    @Operation(summary = "Changes resource password.", description = "Resource for change password.",
            responses = {@ApiResponse(responseCode = "201", description = "Resource change successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",description = "Resource not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/change/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody UserPasswordDto dto) {
        userService.ChangePassword(id,dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg","Password changed successfuly.");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
    @Operation(summary = "Delete resource by id.", description = "Resource for delete user by id, this resource need authentication.",
            responses = {@ApiResponse(responseCode = "201", description = "Resource change successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",description = "Resource not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @SecurityRequirement(name = "basicAuth")
    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity<Map<String,String>> destroy(@PathVariable("id") Long id) {
        UserClient user = userService.findById(id);
        userService.deleteUser(id);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", user.getName() + " has been successfully deleted.");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
