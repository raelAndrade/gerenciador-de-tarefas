package br.com.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tar_tarefa")
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tar_id")
	private Long id;
	
	@Column(name="tar_titulo", length = 50, nullable = false)
	@NotNull(message = "O título é obrigatório!")
	@Length(max = 50, min = 3, message = "O título deve conter entre 3 e 50 caracteres")
	private String titulo;
	
	@Column(name="tar_descricao", length = 100, nullable = true)
	@NotNull(message = "Descrição é obrigatória!")
	@Length(max = 100, message = "A descrição deve conter até 100 caracteres")
	private String descricao;
	
	@Column(name="tar_dataExpiracao", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataExpiracao;
	
	@Column(name="tar_concluida", nullable = false)
	private Boolean concluida = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="usr_id")
	private Usuario usuario;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(Date dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public Boolean getConcluida() {
		return concluida;
	}

	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tarefa [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", dataExpiracao="
				+ dataExpiracao + ", concluida=" + concluida + ", usuario=" + usuario + "]";
	}
	
}
