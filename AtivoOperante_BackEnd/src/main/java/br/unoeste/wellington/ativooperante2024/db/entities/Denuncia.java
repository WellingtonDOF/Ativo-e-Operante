package br.unoeste.wellington.ativooperante2024.db.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="denuncia")
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="den_id")
    private Long id;

    @Column(name="den_titulo")
    private String titulo;

    @Column(name="den_texto")
    private String texto;

    @Column(name="den_urgencia")
    private int urgencia;

    @ManyToOne
    @JoinColumn(name="org_id", nullable = false) // nome da coluna que vai referenciar, e se pode ou não ser nula, nesse caso não pode ser NOT_NULL
    private Orgao orgId;
    //Objeto tipo orgao

    @Column(name="den_data")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name="tip_id", nullable = false)
    private Tipo tipId;

    @ManyToOne
    @JoinColumn(name="usu_id", nullable = false)
    private Usuario usuario;


    @Column(name="den_image")
    private String imageName;

    @OneToOne(mappedBy = "denuncia")
    private Feedback feed;


    public Denuncia() {
        this(0L,"","",0,null, null,null,null,"");
    }

    public Denuncia(Long id, String titulo, String texto, int urgencia, Orgao orgId, LocalDate data, Tipo tipId, Usuario usuario, String imageName) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.urgencia = urgencia;
        this.orgId = orgId;
        this.data = data;
        this.tipId = tipId;
        this.usuario = usuario;
        this.imageName=imageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public Orgao getOrgId() {
        return orgId;
    }

    public void setOrgId(Orgao orgId) {
        this.orgId = orgId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Tipo getTipId() {
        return tipId;
    }

    public void setTipId(Tipo tipId) {
        this.tipId = tipId;
    }

    public Usuario getUsId() {
        return usuario;
    }

    public void setUsId(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
