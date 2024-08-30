-- INSERT ROLES  الادوار
insert into roles (arabic_name, role, english_name) values ("عميل", "ROLE_GUEST", "Guest");
insert into roles (arabic_name, role, english_name) values ("تاجر", "ROLE_LESSOR", "Lessor");
insert into roles (arabic_name, role, english_name) values ("ادمن", "ROLE_ADMIN", "Admin");
insert into roles (arabic_name, role, english_name) values ("خدمة عملاء", "ROLE_SERVICE", "Customer Service");

insert into transactions (arabic_name, english_name) values ("حجز", "Reservation");
insert into transactions (arabic_name, english_name) values ("باقة اعلانات", "Package Ads");
insert into transactions (arabic_name, english_name) values ("شحن", "Charge");

insert into total_transactions (TOTAL_TRANSACTIONS, TOTAL_RESERVATIONS_TRANSACTIONS, TOTAL_SUBSCRIPTIONS_TRANSACTIONS) values (0,0,0);


-- insert cities    المدن
insert into city (arabic_name, city) values ("طرابلس","Tripoli");
insert into city (arabic_name, city) values ("بنغازي","Benghazi");
insert into city (arabic_name, city) values ("مصراتة","Misrata");

-- insert Regions  المناطق
insert into region (arabic_name, region, city_id) values ("حي الأندلس","Al-Andalus neighborhood", 1);
insert into region (arabic_name, region, city_id) values ("الحي الإسلامي","Islamic Quarter", 1);
insert into region (arabic_name, region, city_id) values ("البركة","ُEl baraka", 2);
insert into region (arabic_name, region, city_id) values ("سيدي حسين","Sidi Hussein", 2);
insert into region (arabic_name, region, city_id) values ("الرويسات","ُRuwaisat", 3);
insert into region (arabic_name, region, city_id) values ("الرملة","Ramla", 3);

-- insert unit_type نوع الوحدة

insert into unit_type (type_arabic_name, type_name) values ("اقامات","Residencies");
insert into unit_type (type_arabic_name, type_name) values ("قاعات مناسبات","Event Halls");

-- insert accommodation_type   نوع الاقامة

insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("فندق","Hotel");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("شقة فندقية","Hotel Apartment");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("شقة خارجية","External Apartment");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("شالية","Chalet");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("منتجع","Resort");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("استراحة","Lounge");

insert into types_event_halls  (types_event_halls_arabic_name, types_event_halls_name) values ("قاعة مناسبات","Event hall");
insert into types_event_halls  (types_event_halls_arabic_name, types_event_halls_name) values ("فلل مناسبات","Occasion villas");



insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("نجمة واحدة","One Star");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("نجمتان","Two Stars");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("3 نجوم","Three Stars");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("4 نجوم","Four Stars");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("5 نجوم","Five Stars");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("غير مصنف","Unclassified");

-- insert room_available الغرف المتاحة 

insert into room_available (room_available_name_arabic, room_available_name) values ("غرفة فردية","Single Room");
insert into room_available (room_available_name_arabic, room_available_name) values ("غرفة زوجية بسرير واحد","Double Room Has One Bed");
insert into room_available (room_available_name_arabic, room_available_name) values ("غرفة زوجية بسريرين","Double Room Has Two Bed");
insert into room_available (room_available_name_arabic, room_available_name) values ("جناح","Suite");

-- insert feature المميزات 
insert into feature (feature_arabic_name, feature_name) values ("اطلالة علي البحر","Sea View");
insert into feature (feature_arabic_name, feature_name) values ("اطلالة علي الجبل","Mountain view");
insert into feature (feature_arabic_name, feature_name) values ("اطلالة علي الحديقة","Garden view");
insert into feature (feature_arabic_name, feature_name) values ("مصعد","elevator");
insert into feature (feature_arabic_name, feature_name) values ("انترنت","Internet");
insert into feature (feature_arabic_name, feature_name) values ("افطار صباحي","Morning breakfast");
insert into feature (feature_arabic_name, feature_name) values ("شاطئ خاص","Private beach");
insert into feature (feature_arabic_name, feature_name) values ("شاطئ خاص","Private beach");
insert into feature (feature_arabic_name, feature_name) values ("موقف سيارة","Parking");
insert into feature (feature_arabic_name, feature_name) values ("مكتب أمن","Security office");


-- insert sub_feature مميزات فرعية 
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("ألعاب اطفال","toys");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("تدفئة","heating");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("غسالة صحون","dishwasher");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("غسالة ملابس","washing machine");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("توصيل واستقبال من المطار","airport pick-up and drop-off");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("ركن شواء","Grill corner");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("شاشة","screen");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("كرسي مساج","chair massage");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("مكان مناسب لذوي الاحتياجات الخاصة","suitable place for people with special needs");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("تكيف","air conditioning");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("حمام سباحة","Swimming Pool");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("بلاي ستيشن","Play Station");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("أواني طبخ","Cooking utensils");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("مايكرويف","Microwave");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("ثلاجة","refrigerator");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("فرن","oven");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("جاكوزي","Jacuzzi");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("ساونا","sauna");


-- insert status_unit   	حالة الوحدة

insert into status_unit (status_arabic_name, status_name) values ("انتظار","Pending");
insert into status_unit (status_arabic_name, status_name) values ("المقبولة","Accepted");
insert into status_unit (status_arabic_name, status_name) values ("المرفوضة","Rejected");
insert into status_unit (status_arabic_name, status_name) values ("الملغاة","Canceled");

-- insert feature halls    مميزات لقاعات المناسبات
insert into feature_halls (FEATURE_ARABIC_NAME, FEATURE_NAME) values ("خدمات الضيافة والسفرجة","hospitality and backing services");
insert into feature_halls (FEATURE_ARABIC_NAME, FEATURE_NAME) values ("الديكور","decoration");
insert into feature_halls (FEATURE_ARABIC_NAME, FEATURE_NAME) values ("سيرفز","servers");

insert into feature_halls (FEATURE_ARABIC_NAME, FEATURE_NAME) values ("شاشات عملاقة","giant screens");
insert into feature_halls (FEATURE_ARABIC_NAME, FEATURE_NAME) values ("مؤثرات ضوئية","lighting effects");
insert into feature_halls (FEATURE_ARABIC_NAME, FEATURE_NAME) values ("تبخير وتعطير","fumigation and perfuming");

-- insert available_periods    الفترات المتاحة
insert into available_periods (AVAILABLE_PERIODS_ARABIC_NAME, AVAILABLE_PERIODS_NAME) values ("من 11 صباحا الي 3 مساءا
","From 11 am to 3 pm");
insert into available_periods (AVAILABLE_PERIODS_ARABIC_NAME, AVAILABLE_PERIODS_NAME) values ("من 5 مساءا الي 12 صباحا","From 5 pm to 12 am");

-- insert package_ads           اضافة باقة اعلانات
insert into package_ads (ads_arabic_name, ads_name, number_ads, price) values ("الفضية", "Silver", 5, 100);
insert into package_ads (ads_arabic_name, ads_name, number_ads, price) values ("الذهبية", "Gold", 10, 150);
insert into package_ads (ads_arabic_name, ads_name, number_ads, price) values ("الماسية", "Diamond", 15, 200);

-- insert available_area        الغرف المتاحة للشقق والشقق الخارجية والمنتجعات
insert into available_area (available_area_name_arabic, available_area_name) values ("استوديو","studio");
insert into available_area (available_area_name_arabic, available_area_name) values ("غرفتين","two rooms");
insert into available_area (available_area_name_arabic, available_area_name) values ("ثلاث غرف","three rooms");

insert into types_apartment (types_apartment_arabic_name, types_apartment_name) values ("استوديو","studio");
insert into types_apartment (types_apartment_arabic_name, types_apartment_name) values ("غرفتين","two rooms");
insert into types_apartment (types_apartment_arabic_name, types_apartment_name) values ("ثلاث غرف","three rooms");

insert into evaluation (evaluation_arabic_name, evaluation_name, score) values ("+ممتاز: 9", "Excellent: 9+", 9);
insert into evaluation (evaluation_arabic_name, evaluation_name, score) values ("+جيد جدا: 8", "Very Good: 8+", 8);
insert into evaluation (evaluation_arabic_name, evaluation_name, score) values ("+جيد: 7", "Good: 7+", 7);
insert into evaluation (evaluation_arabic_name, evaluation_name, score) values ("+مرضي: 6", "Acceptable: 6+", 6);