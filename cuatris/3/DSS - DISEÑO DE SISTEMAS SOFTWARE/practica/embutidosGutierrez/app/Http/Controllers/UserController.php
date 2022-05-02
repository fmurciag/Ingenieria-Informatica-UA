<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use App\User;

class UserController extends Controller
{
    public function showAll(Request $request) {
        $res = $request->get('ordenarUser');
        if(!$res)
        {
            $res = 'id';
        }
        $users = User::orderBy($res,'asc')->paginate(5);
        return view('users/usersIndex', compact('users'));
    }
    public function showUser($id) {
        $user = User::find($id);
        if(!$user){
            return view('NotFound');
        }
        return view('users/usersShow',['user' => $user]);

        
    }

    public function buscarUsuarioPorNombre() {
        $nombre=request('nombre');
        $users = User::all()->where('nombre',$nombre);
        return view('users/usersShowByName', compact('users'));
    }

    public function createUser(){
        return view('users/usersCreate');
    }
    public function storeUsers(){

        $name = request('name');
        $secondnames = request('secondnames');
        $email = request('email');
        $password = request('password');
        $tlf = request('tlf');
        $admin = request('admin');
        $direccion = request('direccion');
        $pago = request('pago');

        request()->validate([
            'name' => 'required',
            'secondnames' => 'required',
            'email' => 'required|unique:users|max:255|email',
            'password' => 'required',
            'tlf' => 'required|digits:9',
            'admin' => 'required|boolean',
            'pago' => 'nullable|digits:9'
        ]);

        User::create([

            'nombre' => $name,
            'apellidos' => $secondnames,
            'email' => $email,
            'password' => bcrypt($password),
            'telefono' => $tlf,
            'esAdmin' => settype($admin, 'boolean'),
            'direccion' => $direccion,
            'pago' => $pago,

        ]);

        return redirect()->route('users.showAll');

    }

    public function editUser($id){
        $user = User::findOrfail($id);
        return view('users.usersEdit', [
            'user' => $user
        ]);
    }

    public function editProfile($id){
        $user = User::findOrfail($id);
        return view('users.editProfile', [
            'user' => $user
        ]);
    }

    public function contactUser(){
        return view('cliente.contacto');
    }

    public function contactValidate(){

        request()->validate([

            'name' => 'required',
            'secondname' => 'required',
            'email' => 'required|email',
            'phone' => 'nullable'


        ]);

        return redirect()->back()->with('flash', '¡Enviado, se contactará con usted pronto!');
    }

    public function updateUser(User $user){

        request()->validate([
            'name' => 'required',
            'secondnames' => 'required',
            'password' => 'required',
            'tlf' => 'required|digits:9',
            'admin' => 'required|boolean',
            'pago' => 'nullable|digits:9'
        ]);

        $user->update([
            'nombre' => request('name'),
            'apellidos' => request('secondnames'),
            'password' => bcrypt(request('password')),
            'telefono' => request('tlf'),
            'esAdmin' => request('admin'),
            'direccion' => request('direccion'),
            'pago' => request('pago'),

        ]);

            return redirect()->route('users.showAll');
    }

    public function deleteUser(User $user){
        $user->delete();
        return redirect()->route('users.showAll');
    }

    public function storeUser(Request $req){
        
        $name = $req->input('name');
        $surname = $req->input('surname');
        $email = $req->input('email');
        $password = bcrypt($req->input('password'));
        
        $req->validate([
            'name' => 'required|string|max:255',
            'surname' => 'required|string|max:255',
            'email' => 'required|unique:users|max:255|email',
            'password' => 'required|string|min:8|confirmed',
        ]);

        User::create([
            'nombre' => $name,
            'apellidos' => $surname,
            'email' => $email,
            'password' => $password,
            'esAdmin' => false 
        ]);

        return redirect('login')->with('flash', 'Registrado con éxito, Inicia sesión!');
    }

    public function updateProfile(User $user){

        $name = request('name');
        $surname = request('surname');
        $password = bcrypt(request('password'));
        $telephone = request('telephone');
        $dir = request('dir');
        $pago = request('payment');

        request()->validate([
            'name' => 'required|string|max:255',
            'surname' => 'required|string|max:255',
            'password' => 'required|string|min:8|confirmed',
            'telephone' => 'nullable|string|min:9|',
            'dir' => 'nullable|string|max:255',
            'pago' => 'nullable|string|max:127'
        ]);

        $user->update([
            'nombre' => $name,
            'apellidos' => $surname,
            'password' => $password,
            'telefono' => $telephone,
            'direccion' => $dir,
            'pago' => $pago
        ]);
    
        return redirect()->back()->with('flash', '¡Usuario actualizado con éxito!');
    }
}
