package net.codinux.log

fun getCurrentThreadName(): String? {
    /*
    // global:

      // Lookup SetThreadDescription - the docs state we must use runtime-linking of
  // kernelbase.dll, so that is what we do.
  HINSTANCE _kernelbase = LoadLibrary(TEXT("kernelbase.dll"));
  if (_kernelbase != nullptr) {
    _GetThreadDescription =
      reinterpret_cast<GetThreadDescriptionFnPtr>(
                                                  GetProcAddress(_kernelbase,
                                                                 "GetThreadDescription"));

     // actual method:

          HANDLE current = GetCurrentThread();

        PWSTR thread_name;
        HRESULT hr2 = _GetThreadDescription(current, &thread_name);
        if (FAILED(hr2)) {
          log_debug(os, thread)("set_native_thread_name: GetThreadDescription failed!");
        } else {
          int res = CompareStringW(LOCALE_USER_DEFAULT,
                                   0, // no special comparison rules
                                   unicode_name,
                                   -1, // null-terminated
                                   thread_name,
                                   -1  // null-terminated
                                   );
          assert(res == CSTR_EQUAL,
                 "Name strings were not the same - set: %ls, but read: %ls", unicode_name, thread_name);
          LocalFree(thread_name);


          // or:

          HRESULT hr = GetThreadDescription(ThreadHandle, &data);
        if (SUCCEEDED(hr))
        {
            wprintf(“%ls\n”, data);
            LocalFree(data);
        }


        // availability:
        Minimum supported client	Windows 10, version 1607 [desktop apps | UWP apps]
        Minimum supported server	Windows Server 2016 [desktop apps | UWP apps]
        Target Platform	Windows
        Header	processthreadsapi.h
        Library	Kernel32.lib
        DLL	Kernel32.dll

     */

    return null
}