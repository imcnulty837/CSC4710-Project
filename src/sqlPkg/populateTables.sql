use testdb;

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
	values ("john1234@gmail.com", "https://cdn.pixabay.com/photo/2016/09/07/11/37/tropical-1651426__340.jpg", "Night on the beach"),
           ("jane5678@gmail.com", "https://images.unsplash.com/photo-1506102383123-c8ef1e872756?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxleHBsb3JlLWZlZWR8NHx8fGVufDB8fHw%3D&w=1000&q=80", "sun: coming or going?"),
           ("jingle444@gmail.com", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Anatomy_of_a_Sunset-2.jpg/1280px-Anatomy_of_a_Sunset-2.jpg", "an image of a sunset"),
           ("bubble.Gum@gmail.com", "https://cdn.vox-cdn.com/thumbor/MZRJnpwAMIHQ5-XT4FwNv0rivw4=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/19397812/1048232144.jpg.jpg", "this is a sunset image"),
           ("freyja111@gmail.com", "https://cdn3.dpmag.com/2019/10/shutterstock_1239834655.jpg", "just another beautiful day in paradise."),
           ("blue42@gmail.com", "https://images.pexels.com/photos/1210273/pexels-photo-1210273.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "waffles on the beach?"),
           ("echoOne@gmail.com", "https://images.pexels.com/photos/3347244/pexels-photo-3347244.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260", "salmon on the beach"),
           ("test@gmail.com", "https://cf.bstatic.com/images/hotel/max1024x768/213/213099825.jpg", "Had a great time!"),
           ("aquaforce@gmail.com", "https://earthsky.org/upl/2013/09/sunrise-red-sea-Graham-Telford-e1489764712368.jpg", "a simple sunset"),
           ("valarie420@gmail.com", "https://previews.123rf.com/images/cepheia/cepheia1901/cepheia190100030/116755771-japan-traditional-sumi-e-painting-fuji-mountain-sakura-sunset-japan-sun-indian-ink-illustration-japa.jpg", "Japanese Sunset Painting");
           
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
			("john1234@gmail.com", "aquaforce@gmail.com"),
			("john1234@gmail.com", "test@gmail.com"),
			("john1234@gmail.com", "freyja111@gmail.com"),
			("john1234@gmail.com", "bubble.Gum@gmail.com"),
			("john1234@gmail.com", "jingle444@gmail.com"),
			("john1234@gmail.com", "jane5678@gmail.com"),
			("jane5678@gmail.com", "echoOne@gmail.com"),
			("test@gmail.com","aquaforce@gmail.com"),
			("valarie420@gmail.com","blue42@gmail.com");
			
insert into comments(imageId, email, comment)
	values (1,"john1234@gmail.com","Cool pic"),
			(1,"jane5678@gmail.com","ur so lucky!!1"),
			(2,"john1234@gmail.com","what a pretty sunset"),
			(3,"jingle444@gmail.com","where did you get that pic?"),
			(4,"jane5678@gmail.com","omg its beautiful!"),
			(4,"test@gmail.com","noice"),
			(4,"john1234@gmail.com","Very cool!"),
			(5,"test@gmail.com","love it"),
			(6,"jingle444@gmail.com","kinda lame tbh"),
			(7,"john1234@gmail.com","Pretty pic, maybe a better angle next time");
	
insert into ImageTag(imageId, tagId)
	values (1,1),
			(1,2),
			(1,3),
			(2,1),
			(3,1),
			(3,2),
			(4,1),
			(5,1),
			(6,1),
			(7,1);
	
insert into likes(email, imageId, likeSwitch)
	values ("john1234@gmail.com",1,true),
			("jane5678@gmail.com",1,true),
			("jingle444@gmail.com",1,true),
			("john1234@gmail.com",2,true),
			("jane5678@gmail.com",3,true),
			("jingle444@gmail.com",4,true),
			("test@gmail.com",4,true),
			("jingle444@gmail.com",5,true),
			("jane5678@gmail.com",4,true),
			("john1234@gmail.com",5,true);