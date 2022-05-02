<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Support\Facades\Auth;

class EsAdmin {
    public function handle($request, Closure $next)
    {
        $user = Auth::user();
        if($user && $user->esAdmin == true) {
            return $next($request);
        } else {
            return redirect()->route('casa');
        }
    }
}
