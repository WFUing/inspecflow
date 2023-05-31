package org.example.flowable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {
    String name;
    String id;
    String assignee;
    String formName;
}
