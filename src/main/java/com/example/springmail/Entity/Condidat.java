package com.example.springmail.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Condidat {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser;


    private String username;

    private String prenom;

    private String nom;

    private Date dateOfBirth;

    private String phone;

    private String email;
    private String cin;
    private String adresse ;
    private String ville;
    private String nationality;

    private String pathCv;
    private String pathMotivationLetter;
    private String niveauEtud;
    private String titreDiplome;
    private String university;
    private String niveauExp;
    private String experience;
    private Boolean archived ;
}
