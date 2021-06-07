insert into online_sessions (id) values (1);

insert into procesars (id, items, idField, nameField, onlineSessionId) values (1, '[{"id": "123456", "name": "test", "status": "OK"}]', 'id', 'name', 1);

insert into custom_fields (id, displayName, key, procesarId) values (1, 'Status', 'status', 1);

insert into progress_fields (id, field, firstState, secondState, procesarId) values (1, 'status', 'OK', 'NO-OK', 1);
