package com.spectra.consumer.Models;

import activeandroid.Model;
import activeandroid.annotation.Column;
import activeandroid.annotation.Table;
import activeandroid.query.Select;

@Table(name = "UserImageData")
public class UserImageData extends Model {
    @Column(name = "CANId" ,unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String CANId;
    @Column(name = "UserImage")
    public String UserImage;

    public UserImageData(){
        super();
    }
    public static UserImageData get(String CANId){
        return new Select().from(UserImageData.class).where("CANId = ?",CANId).executeSingle();
    }
}
