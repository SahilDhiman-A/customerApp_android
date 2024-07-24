package com.spectra.consumer.service.model.Response;

import java.util.List;
import java.util.Objects;

public class FAQ {
     String _id;
     String name;
     Boolean is_deleted;
     Boolean is_active;
     String created_at;
     String updated_at;
     String __v;
     CategoryInfo category_info;
     List<FaqInfo> faq_info;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CategoryInfo getCategory_info() {
        return category_info;
    }

    public void setCategory_info(CategoryInfo category_info) {
        this.category_info = category_info;
    }

    public List<FaqInfo> getFaq_info() {
        return faq_info;
    }

    public void setFaq_info(List<FaqInfo> faq_info) {
        this.faq_info = faq_info;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FAQ faq = (FAQ) o;
        return Objects.equals(get_id(), faq.get_id()) &&
                Objects.equals(getName(), faq.getName()) &&
                Objects.equals(getIs_deleted(), faq.getIs_deleted()) &&
                Objects.equals(getIs_active(), faq.getIs_active()) &&
                Objects.equals(getCreated_at(), faq.getCreated_at()) &&
                Objects.equals(getUpdated_at(), faq.getUpdated_at()) &&
                Objects.equals(get__v(), faq.get__v()) &&
                Objects.equals(getCategory_info(), faq.getCategory_info()) &&
                Objects.equals(getFaq_info(), faq.getFaq_info());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_id(), getName(), getIs_deleted(), getIs_active(), getCreated_at(), getUpdated_at(), get__v(), getCategory_info(), getFaq_info());
    }
}

