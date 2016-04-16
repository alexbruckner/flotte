package com.zuehlke.camp.flotte.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LocationUpdate.
 */

@Document(collection = "location_update")
public class LocationUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("source")
    private String source;

    @NotNull
    @Field("time")
    private ZonedDateTime time;

    @NotNull
    @Field("longitude")
    private Double longitude;

    @NotNull
    @Field("lattitude")
    private Double lattitude;

    @Field("comment")
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationUpdate locationUpdate = (LocationUpdate) o;
        if(locationUpdate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, locationUpdate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocationUpdate{" +
            "id=" + id +
            ", source='" + source + "'" +
            ", time='" + time + "'" +
            ", longitude='" + longitude + "'" +
            ", lattitude='" + lattitude + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
