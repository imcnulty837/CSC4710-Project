insert into User(email, firstName, lastName, password, birthday, gender) 
	values ("john1234@gmail.com", "John", "Smith", "john1234", "1999-02-20", "male"),
		 	("jane5678@gmail.com", "Jane", "Doe","jane5678", "1969-04-20", "female"),
	 	 	("jingle444@gmail.com", "Jingle", "heimer-schmidt","jingle444", "1984-02-03", "male"),
		 	("bubble.Gum@gmail.com", "Jacob", "heimer-schmidt","bubble.Gum", "2002-02-04", "male"),
		 	("freyja111@gmail.com", "Freyja", "Aesmiri","Freyja111", "1947-06-11", "female"),
		 	("blue42@gmail.com", "katalyn", "Dinkleburger","blue42", "1997-04-04", "female"),
			("echoOne@gmail.com", "Nick", "Foster","echoOne", "1993-05-15", "male"),
			("test@gmail.com", "test", "test","test", "2012-07-14", "female"),
			("aquaforce@gmail.com", "Jeff", "Himagain","aquaforce", "1995-05-05", "male"),
			("valarie420@gmail.com", "Valerie", "Schmidt","val420", "1977-12-24", "female");
			
insert into image(email, url, description)
	values ("john1234@gmail.com", "placeholderURL", "an image of a sunset"),
           ("jane5678@gmail.com", "placeholderURL", "an image of a sunset"),
           ("jingle444@gmail.com", "placeholderURL", "an image of a sunset"),
           ("bubble.Gum@gmail.com", "placeholderURL", "an image of a sunset"),
           ("freyja111@gmail.com", "placeholderURL", "an image of a sunset"),
           ("blue42@gmail.com", "placeholderURL", "an image of a sunset"),
           ("echoOne@gmail.com", "placeholderURL", "an image of a sunset"),
           ("test@gmail.com", "placeholderURL", "an image of a sunset"),
           ("aquaforce@gmail.com", "placeholderURL", "an image of a sunset"),
           ("valarie420@gmail.com", "placeholderURL", "an image of a sunset");
           
insert into tags(tag)
	values ("Michigan"),
			("Huron"),
			("Ontario"),
			("Erie"),
			("Superior"),
			("Atlantic"),
			("Pacific"),
			("Tahoe"),
			("Mississippi"),
			("Gladwin");
			
insert into follows(followerEmail, followeeEmail)
	values ("john1234@gmail.com", "echoOne@gmail.com"),
			("test@gmail.com","aquaforce@gmail.com"),
			("valarie420@gmail.com","blue42@gmail.com");
			
insert into comments(imageId, email, comment)
	values (1,"john1234@gmail.com","Cool pic");
	
insert into ImageTag(imageId, tagId)
	values (1,1);
	
insert into likes(email, imageId, likeSwitch)
	values ("john1234@gmail.com",1,true);