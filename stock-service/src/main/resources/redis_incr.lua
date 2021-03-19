--
-- 库存扣减
-- User: fuhx
-- Date: 2021/3/19
-- Time: 19:48
-- To change this template use File | Settings | File Templates.

local res = {0}
res[2] = redis.call('INCRBY', KEYS[1], ARGV[1])
res[3] = redis.call('INCRBY', KEYS[2], ARGV[2])
if res[2] < 0 or res[3] < 0 then
    res[1] = -1
    redis.call('DECRBY', KEYS[1], ARGV[1])
    redis.call('DECRBY', KEYS[2], ARGV[2])
end
if res[1] == 0 then
    local expire1 = tonumber(ARGV[3])
    if expire1 < 0 then
        redis.call('PERSIST', KEYS[1])
    elseif expire1 > 0 then
        redis.call('EXPIRE', KEYS[1], expire1)
    end
    local expire2 = tonumber(ARGV[4])
    if expire2 < 0 then
        redis.call('PERSIST', KEYS[2])
    elseif expire2 > 0 then
        redis.call('EXPIRE', KEYS[2], expire2)
    end
end
return res