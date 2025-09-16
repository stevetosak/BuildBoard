package com.db.finki.www.build_board.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddRoleDTO {
    @JsonProperty
    String name;
    @JsonProperty
    String projectTitle;

    @JsonProperty
    List<String> globalPermissions;

    @JsonProperty
    List<PermissionResourceDTO> permissionResourceDTOS;

    @JsonProperty
    String permissionOverrideType;
}
