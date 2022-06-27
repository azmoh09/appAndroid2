package com.example.instgran.Model;

import java.io.Serializable;

public class ModelUser implements Serializable {
    String userName,id,content,date,userImg,idPost,contentComment,numberComments,numberLike,dataLike,idComment;




    public ModelUser() {
    }

    public ModelUser(String userName, String id, String content, String date, String userImg, String idPost) {
        this.userName = userName;
        this.id = id;
        this.content = content;
        this.date = date;
        this.userImg = userImg;
        this.idPost = idPost;

    }

    public String getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(String numberLike) {
        this.numberLike = numberLike;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }




}
