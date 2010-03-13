#! /usr/bin/perl

use strict;
use warnings;

# Проверяю входные параметры
unless ($ARGV[0]) {
    die "Ошибка: коментарий не указан\n";
}

# Переменная в которую я сохраню номер ветки в которой я сейчас работаю
# (номер ветки равен номеру тикета)
my $ticket;

# Переменная в которую я буду сохранять вывод
my $result;

# Узнаю имя ветки
$result = `svn info|grep "URL:"`;
chomp ($result);
$result =~ /\/(\d*)$/;

unless ($1) {
    die "Ошибка: рабочая копия не ветка";
}

$ticket = $1;

# Создаею ветку с именем номера тикета. В комменатрии пишиет "Создаю ветку для работы над тикетом. See #1234."
execute_command(qq{svn ci -m '$ARGV[0]. See #} .  qq{$ticket.'});

sub execute_command {
    my $command = shift;
    print "\$ $command\n";
    my $result = `$command`;
    print $result;
}

