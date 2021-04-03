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
create view NewImages as
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
