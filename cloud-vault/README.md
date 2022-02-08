# Errors id

### [xxx] [iii]
**x - http status**    
**i - error id**  

|  Code  	|                            Description                           	|
|:------:	|:----------------------------------------------------------------:	|
| 404001 	| NotFoundException. Запись в бд не найдена.                       	|
| 400002 	| BadCredentialsException. Логин или пароль введены неверно.       	|
| 401003 	| ExpiredJwtException. Срок действия токена истек.                 	|
| 401003 	| UnsupportedJwtException. Неверный формат JWT токена.             	|
| 401003 	| MalformedJwtException. Неверный JWT токен.                       	|
| 401003 	| SignatureException. Неверная подпись.                            	|
| 400004 	| NullPointerException. Ошибка загрузки файла.                      |
| 400004 	| IOException. Ошибка загрузки файла.                              	|
| 400005 	| NotFoundException. Ошибка ввода.                       	        |
| 400006 	| IllegalArgumentException. Limit не может быть меньше 0.           |
