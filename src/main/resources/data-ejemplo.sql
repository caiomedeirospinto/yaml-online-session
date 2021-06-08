insert into online_sessions (id) values (1);

insert into procesars (id, items, id_field, name_field, online_session_id) values (1, '[{"id": "123456", "name": "test", "status": "OK"}]', 'id', 'name', 1);

insert into custom_fields (id, display_name, key_field, procesar_id) values (1, 'Status', 'status', 1);

insert into progress_fields (id, field, first_state, second_state, procesar_id) values (1, 'status', 'OK', 'NO-OK', 1);
