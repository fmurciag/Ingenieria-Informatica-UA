<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Support\Facades\Auth;

class Authorized {
    public function handle($request, Closure $next)
    {
        $id = $request->route('id');
        if($id) {
            $user = Auth::user();
            if($user && $user->id == $id) {
                return $next($request);
            } else {
                return redirect()->route('casa');
            }
        }
    }
}
