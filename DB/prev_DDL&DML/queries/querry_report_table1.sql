t1
select r.name as row_name, m.year as col_name, m.morbidity as morbidity_abs ,ps.value as population from morbidityolap m
            left join population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
            inner join region r ON m.region_id=r.id
            where 1=1
            and m.localization_id=1
            and m.sex_id=3
            and m.age_id=10 
            group by m.region_id, m.year
            order by m.region_id, m.year;

t2
 select age.name as row_name, m.year as col_name, m.morbidity as morbidity_abs, ps.value as population
 from morbidityolap m 
 left join  population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
 inner join age age ON m.age_id=age.id
 where 1=1
 and m.region_id=17
 and m.sex_id=3
 and m.localization_id=1
 and (age.name = 'все возраста'
 or age.name = '0-4'
 or age.name = '5-9'
 or  age.name ='10-14'
 or  age.name ='15-19'
 or  age.name ='20-24'
 or  age.name ='25-29'
 or  age.name ='30-34'
 or  age.name ='35-39'
 or age.name  ='40-44'
 or age.name  ='45-49'
 or age.name  ='50-54'
 or age.name  ='55-59'
 or age.name  ='60-64'
 or age.name  ='65-69'
 or  age.name ='70 +')
 group by m.age_id, m.year
 order by m.age_id, m.year;
 
 t3
 select s.name as row_name, m.year as col_name, m.morbidity as morbidity_abs , ps.value as population from morbidityolap m
            left join population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
            inner join sex s ON m.sex_id=s.id
            where 1=1
            and m.localization_id=1
            and m.age_id=10 
            and m.region_id=17
            group by m.sex_id, m.year
            order by m.sex_id, m.year;
			
t4
select m.stage_id as row_name, m.year as col_name, m.value as morbidity_abs, ps.value as population
from morbidityolap2100 m
left join population ps on m.region_id=ps.region_id and m.year=ps.year
where 1=1 
and m.region_id=17 
and m.localization_id=1
and ps.sex_id=3
group by m.stage_id, m.year
order by  m.stage_id,  m.year

t5
select l.name as row_name, m.year as col_name, m.morbidity as morbidity_abs ,ps.value as population 
from morbidityolap m
left join population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
inner join `localization` l ON m.`localization_id`=l.id
where 1=1
and m.`region_id`=1
and m.sex_id=3
and m.age_id=10 
group by m.localization_id, m.year
order by m.localization_id, m.year;