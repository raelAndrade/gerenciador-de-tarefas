package br.com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "usr_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usr_id")
	private Long id;
	
	@Column(name = "usr_email", nullable = false, length = 100)
	@NotNull(message = "E-mail é obrigatório.")
	@Length(min = 5, max = 100, message = "E-mail deve conter entre 5 e 100 caracteres")
	@Email(message = "E-mail inválido!")
	private String email;
	
	@Column(name = "usr_senha", nullable = false, length = 100)
	@NotNull(message = "A senha é obrigatório.")
	private String senha;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}