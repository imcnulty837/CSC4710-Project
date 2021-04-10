use testdb;

-- creates cool images view *images that have 5 or more likes *
create view coolImages as
    select * from image
    where imageId in (
	select imageId 
    from likes
    group by imageId
    having count(imageId) >= 5
);

-- images that have been posted in the last day
create view recentImages as
    select * from image
    where ts between date_sub(now(), interval 1 day) and now();

-- top 3 liked posts in images
create view viralImages as
    select i.*
    from image i
    left join likes l on l.imageId = i.imageId
    group by i.imageId
    order by count(l.imageId) desc
    limit 0, 3;

-- show the users who have the most posts
create view topUsers as
    select email
    from image
    group by email
    having count(email) = (
	select max(counted) from (
		select *, count(email) counted
		from image
		group by email
	) as maxCount
);

--  [Popular users]: List those users that  are followed by at least 5 followers.  
create view popularUsers as
    select *
    from follows
    group by followeeEmail
    having count(followeeEmail) >= 5;

-- topTags: List those tags used by at least 3 people 
create view topTags as
    select distinctrow t.tag from tags t, imagetag i
    where t.tagId = i.tagId
    and i.tagId in (
	select tagId
    from imageTag
    having count(imageId) >= 3
);

-- positiveUsers: List those users that like every post of the users they follow
create view positiveUsers as
    select distinctrow u.email from user u, image i
    where i.email in (
	    select followerEmail
        from follows
        where u.email = followeeEmail
    ) and i.imageId in (
	    select imageId
        from likes
        having count(imageId) >= 1
);

-- poorImages: List those images with no likes and no comments
-- Not sure if this one works
create view poorImages as 
    select * from image
    where imageId not in (
	    select c.imageId 
        from likes l
        inner join comments c
        on c.imageId = l.imageId
        group by c.imageId
);

-- inactiveUsers: List those users who have not posted an image, followed a user, left a like, or commented
create view inactiveUsers as
    select distinctrow email from user
    where email not in (
	    select email from image
    ) and email not in (
	    select followeeEmail from follows
    ) and email not in (
	    select email from likes
    ) and email not in (
	    select i.email from image i
    where i.imageId in (
		select c.imageId from comments c
        where c.comment = ""
	)
);