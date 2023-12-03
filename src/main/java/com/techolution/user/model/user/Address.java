package com.techolution.user.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techolution.user.model.audit.UserDateAudit;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address extends UserDateAudit {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "suite")
    private String suite;

    @Column(name = "city")
    private String city;

    @Column(name = "zipcode")
    private String zipcode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geo_id")
    private Geo geo;

    @OneToOne(mappedBy = "address")
    private User user;

    @JsonIgnore
    public Long getId() {
        return id;
    }
}
