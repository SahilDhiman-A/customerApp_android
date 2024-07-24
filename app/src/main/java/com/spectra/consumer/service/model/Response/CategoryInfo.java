package com.spectra.consumer.service.model.Response;

import java.util.Objects;

public class CategoryInfo {
    String _id;
    String name;
    Boolean is_deleted;
    Boolean is_active;
    String segment_id;
    String created_at;
    String updated_at;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryInfo that = (CategoryInfo) o;
        return Objects.equals(get_id(), that.get_id()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getIs_deleted(), that.getIs_deleted()) &&
                Objects.equals(getIs_active(), that.getIs_active()) &&
                Objects.equals(getSegment_id(), that.getSegment_id()) &&
                Objects.equals(getCreated_at(), that.getCreated_at()) &&
                Objects.equals(getUpdated_at(), that.getUpdated_at()) &&
                Objects.equals(get__v(), that.get__v());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_id(), getName(), getIs_deleted(), getIs_active(), getSegment_id(), getCreated_at(), getUpdated_at(), get__v());
    }

    String __v;

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

    public String getSegment_id() {
        return segment_id;
    }

    public void setSegment_id(String segment_id) {
        this.segment_id = segment_id;
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



}

