package com.toursandtravel.dto;

import com.toursandtravel.entity.Type;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class TypeResponseDto extends CommonApiResponse{
    private List<Type> transports = new ArrayList<>();

}

