package com.zhd.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Serie {

    private String name;

    private List data;

    private String color;
}
