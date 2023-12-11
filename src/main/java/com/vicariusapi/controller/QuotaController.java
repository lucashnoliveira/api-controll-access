package com.vicariusapi.controller;

import com.vicariusapi.model.User;
import com.vicariusapi.model.UserQuota;
import com.vicariusapi.service.interfaces.QuotaService;
import com.vicariusapi.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quotas")
public class QuotaController {

    private final QuotaService quotaService;

    @Autowired
    public QuotaController(QuotaService quotaService) {
        this.quotaService = quotaService;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Do the process consuming the quota for the user",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    })
    @PostMapping("/consume/{userId}")
    public ResponseEntity<Object> consumeQuota(@PathVariable Long userId) {
        try {
            quotaService.consumeQuota(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get all the user quotas in database",
                    content = @Content(schema = @Schema(implementation = UserQuota.class))
            )
    })
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<UserQuota>> getUserQuotas() {

        return ResponseEntity.ok(quotaService.getUsersQuota());
    }



}
