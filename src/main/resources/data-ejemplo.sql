insert into online_sessions ('id', 'usersConnected') values (1, '');

insert into procesars ('id', 'items', 'idField', 'nameField', 'onlineSessionId') values (1, '[{"id": "123456", "name": "test", "status": "OK"}]', 'id', 'name', 1);

insert into custom_fields ('displayName', 'key', 'procesarId') values ('Status', 'status', 1);

insert into progress_fields ('field', 'firstState', 'secondState', 'procesarId') values ('status', 'OK', 'NO-OK', 1);
