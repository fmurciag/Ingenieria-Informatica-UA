<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Category;

class CategoryController extends Controller
{
    public function showAll(Request $request){
        $res = $request->get('ordenarCategory');
        if(!$res){
            $res = 'id';
        }
        $categories = Category::orderBy($res,'asc')->paginate(5);
        return view('categories/categoriesIndex', compact('categories'));
    }

    public function showCategory($id){
        $category = Category::find($id);
        if(!$category)
        {
            return view('notFound');
        }
        return view('categories/categoriesShow', ['category' => $category]);
    }

    public function deleteCategory($id){
        $category = Category::find($id);
        $category -> delete();
        return redirect()->route('categories.showCategories');
    }
}
