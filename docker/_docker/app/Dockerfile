FROM php:8.2-fpm

# Install system dependencies
RUN apt-get update && apt-get install -y \
    build-essential libicu-dev libzip-dev libxml2-dev libpng-dev \
    libjpeg62-turbo-dev libfreetype6-dev libonig-dev locales zip \
    unzip jpegoptim optipng pngquant gifsicle vim git curl \
    wget libicu-dev g++ libpq-dev libssl-dev gettext

RUN docker-php-ext-configure zip &&  docker-php-ext-install zip
RUN docker-php-ext-install soap
RUN docker-php-ext-install sockets

# Clear cache
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Install PHP extensions
RUN docker-php-ext-install pdo mbstring exif pcntl bcmath gd opcache

# pgsql
RUN docker-php-ext-install pdo_pgsql pgsql
RUN docker-php-ext-install pdo_mysql mysqli

#Redis
RUN pecl install -o -f redis && docker-php-ext-enable redis

# Get latest Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Create system user to run Composer and Artisan Commands
RUN useradd -G www-data,root -u 1000 -d /home/shareit shareit
RUN mkdir -p /home/shareit/.composer && \
    chown -R shareit:shareit /home/shareit

# Set working directory
WORKDIR /var/www

USER shareit
