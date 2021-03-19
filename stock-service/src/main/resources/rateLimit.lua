--
-- User: fuhx
-- Date: 2021/3/3
-- Time: 22:10
-- desc: 限流
--
--获取客户端传过来的参数
local key = KEYS[1];
--获取限制次数
local limit = ARGV[1];
--获取限制的时间段
local expire = ARGV[2];
--对redis的keys的value进行+1操作
local tmp = redis.call('incr', key);
--如果是第一次进行key的操作时
if tmp == 1 then
    redis.call('expire', key, tonumber(expire));
    redis.call('set', 'mystock', limit);
    redis.call('expire', 'mystock', tonumber(expire));
end;
--判断是否大于10，大于就进行限制
--if tmp > tonumber(limit) then
--    return 0;
--end;
local stock = redis.call('get', 'mystock');
if(tonumber(stock) <= 0) then
    return 0;
else
    return redis.call('decr', 'mystock');
end;
--return 1;
