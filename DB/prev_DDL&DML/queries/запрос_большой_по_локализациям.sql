(select l.name as row_name, m.year as col_name, m.morbidity as morbidity_abs ,ps.value as population 
            from morbidityolap m
            left join population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
            inner join `localization` l ON m.`localization_id`=l.id
            where 1=1
            and m.region_id=17
            and m.sex_id=3
            and m.age_id=10 
            and m.`localization_id` in (2,3,4,5,6,7,8,9,10,11,12,13,14,15,23,
            24,25,26,27,28,29,30,31,32,33,34,35,36,41,42)
            group by m.localization_id, m.year
            order by m.localization_id, m.year)
union
(select l.name as row_name, m.year as col_name, m.morbidity as morbidity_abs ,ps.value as population 
            from morbidityolap m
            left join population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
            inner join `localization` l ON m.`localization_id`=l.id
            where 1=1
            and m.region_id=17
            and m.sex_id=2
            and m.age_id=10 
            and m.`localization_id` in (16,17,18,19)
            group by m.localization_id, m.year
            order by m.localization_id, m.year)
union            
(select l.name as row_name, m.year as col_name, m.morbidity as morbidity_abs ,ps.value as population 
            from morbidityolap m
            left join population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
            inner join `localization` l ON m.`localization_id`=l.id
            where 1=1
            and m.region_id=17
            and m.sex_id=1
            and m.age_id=10 
            and m.`localization_id` in (20,21,22)
            group by m.localization_id, m.year
            order by m.localization_id, m.year)
union
(select l.name as row_name, m.year as col_name, m.morbidity as morbidity_abs ,ps.value as population 
            from morbidityolap m
            left join population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
            inner join `localization` l ON m.`localization_id`=l.id
            where 1=1
            and m.region_id=17
            and m.sex_id=3
            and m.age_id=10 
            and m.`localization_id` in (50)
            group by m.localization_id, m.year
            order by m.localization_id, m.year)
union
(select l.name as row_name, m.year as col_name, m.morbidity as morbidity_abs ,ps.value as population 
            from morbidityolap m
            left join population ps ON m.region_id=ps.region_id and m.year=ps.year and m.age_id=ps.age_id and  m.sex_id=ps.sex_id
            inner join `localization` l ON m.`localization_id`=l.id
            where 1=1
            and m.region_id=17
            and m.sex_id=3
            and m.age_id=10 
            and m.`localization_id` in (1)
            group by m.localization_id, m.year
            order by m.localization_id, m.year);