package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Email;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Usuario extends Model{

	@Id
	public Long id;
	
	public String nome;
	
	@Email
	public String email;
	
	public boolean isAdministrador;
	
	public String last_ip;
	
	public String nacionalidade;
	
	public String pais;
	
	public String idade;
	
	public Usuario(){
		
	}
	
	public static void create(Usuario user){
        user.save();
        //test to ocurred error 500
        //user.find.findUnique();
   }
	
	public static Finder<Long, Usuario> find = new Finder<Long, Usuario>(Long.class, Usuario.class);
}
