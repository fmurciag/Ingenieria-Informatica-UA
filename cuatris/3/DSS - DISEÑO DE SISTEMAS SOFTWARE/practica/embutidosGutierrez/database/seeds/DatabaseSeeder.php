<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        $this->call(UsersTableSeeder::class);
        $this->command->info('Users table seeded!');

        $this->call(OrdersTableSeeder::class);
        $this->command->info('Orders table seeded!');

        $this->call(CategoriesTableSeeder ::class);
        $this->command->info('Categories table seeded!');

        $this->call(ProductsTableSeeder ::class);
        $this->command->info('Products table seeded!');

        $this->call(OrderLinesTableSeeder ::class);
        $this->command->info('OrderLines table seeded!');
    }
}
