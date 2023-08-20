package com.optimal.standard.dto;

import java.util.List;

public record TokenInfo(String token, List<String> authorities) {

}
