package com.final_project_leesanghun_team2.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 이거 안해주면 왜 오류날까 Bad Request 400
@AllArgsConstructor
@Getter
public class CommentModifyRequest {
    private String comment;
}
