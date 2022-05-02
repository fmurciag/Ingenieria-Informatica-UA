<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////RUTAS DEL cliente/////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Route::get('/', function () {
    return view('home');
})->name('casa');

/*Route::get('/contacto', function () {
    return view('cliente.contacto');
})->name('contacto');*/

Route::get('/contacto', 'UserController@contactUser')->name('cliente.contacto');

Route::post('/contacto/validado', 'UserController@contactValidate')->name('cliente.contactoValidado');

Route::get('/tienda', 'ProductController@tienda')->name('cliente.tienda');

Route::get('/carrito', 'OrderlineController@showCarrito')->name('cliente.carrito');

Route::get('/carrito/{id}', 'OrderlineController@addProductCarrito')->name('cliente.addProductCarrito');

Route::post('/comprar', 'OrderlineController@comprar')->name('cliente.comprar');

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////REGISTRAR USUARIO/////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Route::post('auth/register', 'UserController@storeUser')->name('users.register');

Route::post('auth/login', 'Auth\LoginController@login')->name('login');

Route::post('auth/logout', 'Auth\LoginController@logout')->name('logout');


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////RUTAS DEL CRUD DE USUARIO ////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Route::get('/user', 'UserController@showAll')->middleware('esAdmin')->name('users.showAll');

Route::get('/user/createuser' , 'UserController@createUser')->middleware('esAdmin')->name('users.createUser');

Route::get('/user/{id}/edit' , 'UserController@editUser')->middleware('esAdmin')->name('users.editUser');

Route::patch('/user/{user}', 'UserController@updateUser')->middleware('esAdmin')->name('users.updateUser');

Route::post('/user', 'UserController@storeUsers')->middleware('esAdmin')->name('users.storeUsers');

Route::post('/user/buscarUsuarioPorNombre', 'UserController@buscarUsuarioPorNombre')->middleware('esAdmin')->name('users.buscarUsuarioPorNombre');

Route::get('/user/{user}/delete', 'UserController@deleteUser')->middleware('esAdmin')->name('users.deleteUser');

Route::get('/user/filter/{name}','UserController@showUserByName')->middleware('esAdmin');

Route::get('/user/{id}', 'UserController@showUser')->middleware('esAdmin');

Route::get('/user/{id}/editProfile' , 'UserController@editProfile')->middleware('authorized')->name('users.editProfile');

Route::post('/user/{user}/updateProfile' , 'UserController@updateProfile')->name('users.updateProfile');

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// RUTAS DEL CRUD DE PRODUCTO //////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


Route::get('/product', 'ProductController@showAll')->middleware('esAdmin')->name('products.showProducts');

Route::get('/product/createproduct' , 'ProductController@createProduct')->middleware('esAdmin')->name('products.createProducts');

Route::post('/product', 'ProductController@storeProducts')->middleware('esAdmin')->name('products.storeProducts');

Route::post('/product/bucarUnoPorId', 'ProductController@buscarUnoPorId')->middleware('esAdmin')->name('products.buscarUnoPorId');

Route::post('/product/buscarUnoPorNombre', 'ProductController@buscarUnoPorNombre')->middleware('esAdmin')->name('products.buscarUnoPorNombre');

Route::get('/product/{id}/editproduct', 'ProductController@editProduct')->middleware('esAdmin')->name('products.editProduct');

Route::patch('/product/{product}', 'ProductController@updateProduct')->middleware('esAdmin')->name('products.updateProduct');

Route::get('/product/{product}/delete', 'ProductController@deleteProduct')->middleware('esAdmin')->name('products.deleteProduct');

Route::get('/product/{id}', 'ProductController@showProduct')->middleware('esAdmin')->name('products.showProduct');


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// RUTAS DEL CRUD DE PEDIDO ////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


Route::get('/order', 'OrderController@showAll')->middleware('esAdmin')->name('orders.showOrders');

Route::get('/order/createorder', 'OrderController@createOrder')->middleware('esAdmin')->name('orders.createOrders');

Route::post('/order','OrderController@storeOrder')->middleware('esAdmin')->name('orders.storeOrder');

Route::get('/order/{id}/editorder','OrderController@editOrder')->middleware('esAdmin')->name('orders.editOrder');

Route::patch('/order/{order}','OrderController@updateOrder')->middleware('esAdmin')->name('orders.updateOrder');

Route::get('/order/{order}/delete', 'OrderController@deleteOrder')->middleware('esAdmin')->name('orders.deleteOrder');

Route::get('/order/{id}', 'OrderController@showOrder')->middleware('esAdmin');

Route::get('/pedidos', 'OrderController@showForClient')->name('orders.showOrdersCliente');//el controlador de pedidos de cliente

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// RUTAS DEL CRUD DE LINEA DE PEDIDO ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Route::get('/orderlines', 'OrderlineController@showAll')->middleware('esAdmin')->name('orderlines.showOrderlines');

Route::get('/orderlines/{id}/delete','OrderlineController@deleteOrderline')->middleware('esAdmin');

Route::get('/orderlines/{id}/deleteClient','OrderlineController@deleteOrderlineClient')->name('orderlines.deleteOrderlineClient');

Route::get('/orderline/{id}', 'OrderlineController@showOrderLine')->middleware('esAdmin');

Route::get('/orderline/check/{id}', 'OrderlineController@checkOrderLine')->name('orderlines.checkOrderLine');


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// RUTAS DEL CRUD DE CATEGORIA /////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Route::get('/category', 'CategoryController@showAll')->middleware('esAdmin')->name('categories.showCategories');

Route::get('/category/{id}/delete','CategoryController@deleteCategory')->middleware('esAdmin')->name('categories.deleteCategory');

Route::get('/category/{id}', 'CategoryController@showCategory')->middleware('esAdmin');




Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');
