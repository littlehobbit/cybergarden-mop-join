/* User structure */
/* 
    username - username аккаунта
    Добавлено

    email - e-mail аккаунта (как один из идентификаторов)
    Ничего против не имею
    
    password - пароль от аккаунта 
    Ничего против не имею

    phone - телефон, привязанный к аккаунту (как один из идентификаторов)
    Ничего против не имею

    image - изображение аккаунта
    Ничего против не имею

    isAdmin - является ли аккаунт администратором
    Добавлено

    student - Объект сотрудника, если есть
*/

module.exports.userStructure = {
    id:0,
    username:"",
    email:"",
    password:"",
    phone: "",
    role: false,
    student: -1,
    token: ""
}