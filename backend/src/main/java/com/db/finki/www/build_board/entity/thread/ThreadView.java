package com.db.finki.www.build_board.entity.thread;

import java.sql.Timestamp;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//
////id,content,created_at,level,parent_id,user_id,type
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class ThreadView {
//    @JsonProperty
//    private Integer id;
//    @JsonProperty
//    private String content;
//    @JsonProperty
//    private Timestamp createdAt =  Timestamp.from(Instant.now());
//    @JsonProperty
//    private Integer level;
//    @JsonProperty
//    private Integer parentId;
//    @JsonProperty
//    private Integer userId;
//    @JsonProperty
//    private String type;
//    @JsonProperty
//    private Integer numReplies;
//    @JsonProperty
//    private Integer numLikes;
//}

public interface ThreadView {
    Integer getId();
    String getContent();
    Timestamp getCreatedAt();
    Integer getLevel();
    Integer getParentId();
    Integer getUserId();
    String getUsername();      // added
    String getAvatarUrl();     // added
    String getType();
    Integer getNumLikes();
    Integer getNumReplies();
}

