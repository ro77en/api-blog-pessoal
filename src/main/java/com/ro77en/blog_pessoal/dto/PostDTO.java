package com.ro77en.blog_pessoal.dto;

import com.ro77en.blog_pessoal.model.Category;

public record PostDTO(String title, String content, Integer userId, Integer categoryId) {
}
