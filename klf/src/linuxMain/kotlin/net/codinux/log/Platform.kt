package net.codinux.log

fun getCurrentThreadName(): String? {
    /*
    template <size_t NAME_SIZE = 100>

    // pthread_t == pthread_self()

    std::string threadName(pthread_t thread) {
        static_assert(
                std::is_invocable_r_v<int, decltype(pthread_getname_np), pthread_t, char*, size_t>, "Invalid pthread_getname_np signature");
        std::array<char, NAME_SIZE> name;
        int result = pthread_getname_np(thread, name.data(), name.size());
        RuntimeAssert(result == 0, "failed to get thread name: %s\n", std::strerror(result));
        // Make sure name is null-terminated.
        name[name.size() - 1] = '\0';
        return std::string(name.data());
    }
     */

    return null
}