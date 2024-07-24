package com.spectra.consumer.service.model.Response;

import java.util.Objects;

public class FaqInfo {
    String _id;
    String question;
    String answer;
    String link;
    String image_url;
    String video_url;
    String thumbs_up_count;
    Boolean is_deleted;
    Boolean is_active;
    String category_id;
    String created_at;
    String updated_at;
    String __v;
    boolean isLike;
    boolean isUnLike;



    boolean IsRequreVideoPlay;
    public boolean isRequreVideoPlay() {
        return IsRequreVideoPlay;
    }

    public void setRequreVideoPlay(boolean requreVideoPlay) {
        IsRequreVideoPlay = requreVideoPlay;
    }
    boolean ieExpended = false;
    private boolean playVideo;

    public boolean isIeExpended() {
        return ieExpended;
    }



    public void setIeExpended(boolean ieExpended) {
        this.ieExpended = ieExpended;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getThumbs_up_count() {
        return thumbs_up_count;
    }

    public void setThumbs_up_count(String thumbs_up_count) {
        this.thumbs_up_count = thumbs_up_count;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isUnLike() {
        return isUnLike;
    }

    public void setUnLike(boolean unLike) {
        isUnLike = unLike;
    }

    @Override
    public String toString(){
        return "FaqInfo{" +
                "_id='" + _id + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", link='" + link + '\'' +
                ", image_url='" + image_url + '\'' +
                ", video_url='" + video_url + '\'' +
                ", thumbs_up_count='" + thumbs_up_count + '\'' +
                ", is_deleted=" + is_deleted +
                ", is_active=" + is_active +
                ", category_id='" + category_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", __v='" + __v + '\'' +
                ", isLike=" + isLike +
                ", isUnLike=" + isUnLike +
                ", ieExpended=" + ieExpended +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaqInfo faqInfo = (FaqInfo) o;
        return isLike() == faqInfo.isLike() &&
                isUnLike() == faqInfo.isUnLike() &&
                isIeExpended() == faqInfo.isIeExpended() &&
                Objects.equals(get_id(), faqInfo.get_id()) &&
                Objects.equals(getQuestion(), faqInfo.getQuestion()) &&
                Objects.equals(getAnswer(), faqInfo.getAnswer()) &&
                Objects.equals(getLink(), faqInfo.getLink()) &&
                Objects.equals(getImage_url(), faqInfo.getImage_url()) &&
                Objects.equals(getVideo_url(), faqInfo.getVideo_url()) &&
                Objects.equals(getThumbs_up_count(), faqInfo.getThumbs_up_count()) &&
                Objects.equals(getIs_deleted(), faqInfo.getIs_deleted()) &&
                Objects.equals(getIs_active(), faqInfo.getIs_active()) &&
                Objects.equals(getCategory_id(), faqInfo.getCategory_id()) &&
                Objects.equals(getCreated_at(), faqInfo.getCreated_at()) &&
                Objects.equals(getUpdated_at(), faqInfo.getUpdated_at()) &&
                Objects.equals(getVideoPlay(), faqInfo.getVideoPlay()) &&
                Objects.equals(get__v(), faqInfo.get__v());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_id(), getQuestion(), getAnswer(), getLink(), getImage_url(), getVideo_url(), getThumbs_up_count(), getIs_deleted(), getIs_active(), getCategory_id(), getCreated_at(), getUpdated_at(), get__v(), isLike(), isUnLike(), isIeExpended(),getVideoPlay());
    }

    public void setVideoPlay(boolean isVideoPlay) {
        playVideo=isVideoPlay;
    }

    public boolean getVideoPlay() {
        return playVideo;
    }


}

