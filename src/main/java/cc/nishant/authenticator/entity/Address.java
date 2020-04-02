package cc.nishant.authenticator.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends Base {

    private Long latitude;

    private Long longitude;

    private String address;

    private String city;

    private String state;
}
