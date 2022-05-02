pragma Warnings (Off);
pragma Ada_95;
with System;
with System.Parameters;
with System.Secondary_Stack;
package ada_main is

   gnat_argc : Integer;
   gnat_argv : System.Address;
   gnat_envp : System.Address;

   pragma Import (C, gnat_argc);
   pragma Import (C, gnat_argv);
   pragma Import (C, gnat_envp);

   gnat_exit_status : Integer;
   pragma Import (C, gnat_exit_status);

   GNAT_Version : constant String :=
                    "GNAT Version: Community 2021 (20210519-103)" & ASCII.NUL;
   pragma Export (C, GNAT_Version, "__gnat_version");

   GNAT_Version_Address : constant System.Address := GNAT_Version'Address;
   pragma Export (C, GNAT_Version_Address, "__gnat_version_address");

   Ada_Main_Program_Name : constant String := "_ada_ejercicio6" & ASCII.NUL;
   pragma Export (C, Ada_Main_Program_Name, "__gnat_ada_main_program_name");

   procedure adainit;
   pragma Export (C, adainit, "adainit");

   procedure adafinal;
   pragma Export (C, adafinal, "adafinal");

   function main
     (argc : Integer;
      argv : System.Address;
      envp : System.Address)
      return Integer;
   pragma Export (C, main, "main");

   type Version_32 is mod 2 ** 32;
   u00001 : constant Version_32 := 16#96b944f0#;
   pragma Export (C, u00001, "ejercicio6B");
   u00002 : constant Version_32 := 16#2e11c0b1#;
   pragma Export (C, u00002, "system__standard_libraryB");
   u00003 : constant Version_32 := 16#eb6e0dda#;
   pragma Export (C, u00003, "system__standard_libraryS");
   u00004 : constant Version_32 := 16#76789da1#;
   pragma Export (C, u00004, "adaS");
   u00005 : constant Version_32 := 16#f64b89a4#;
   pragma Export (C, u00005, "ada__integer_text_ioB");
   u00006 : constant Version_32 := 16#2ec7c168#;
   pragma Export (C, u00006, "ada__integer_text_ioS");
   u00007 : constant Version_32 := 16#7a7a1ff8#;
   pragma Export (C, u00007, "ada__exceptionsB");
   u00008 : constant Version_32 := 16#661890aa#;
   pragma Export (C, u00008, "ada__exceptionsS");
   u00009 : constant Version_32 := 16#19b42e05#;
   pragma Export (C, u00009, "ada__exceptions__last_chance_handlerB");
   u00010 : constant Version_32 := 16#fc9377ef#;
   pragma Export (C, u00010, "ada__exceptions__last_chance_handlerS");
   u00011 : constant Version_32 := 16#a2da961d#;
   pragma Export (C, u00011, "systemS");
   u00012 : constant Version_32 := 16#adf22619#;
   pragma Export (C, u00012, "system__soft_linksB");
   u00013 : constant Version_32 := 16#ee9070f7#;
   pragma Export (C, u00013, "system__soft_linksS");
   u00014 : constant Version_32 := 16#6c2097a7#;
   pragma Export (C, u00014, "system__secondary_stackB");
   u00015 : constant Version_32 := 16#5371a199#;
   pragma Export (C, u00015, "system__secondary_stackS");
   u00016 : constant Version_32 := 16#896564a3#;
   pragma Export (C, u00016, "system__parametersB");
   u00017 : constant Version_32 := 16#e58852d6#;
   pragma Export (C, u00017, "system__parametersS");
   u00018 : constant Version_32 := 16#ced09590#;
   pragma Export (C, u00018, "system__storage_elementsB");
   u00019 : constant Version_32 := 16#8f19dc19#;
   pragma Export (C, u00019, "system__storage_elementsS");
   u00020 : constant Version_32 := 16#ce3e0e21#;
   pragma Export (C, u00020, "system__soft_links__initializeB");
   u00021 : constant Version_32 := 16#5697fc2b#;
   pragma Export (C, u00021, "system__soft_links__initializeS");
   u00022 : constant Version_32 := 16#41837d1e#;
   pragma Export (C, u00022, "system__stack_checkingB");
   u00023 : constant Version_32 := 16#2c65fdf5#;
   pragma Export (C, u00023, "system__stack_checkingS");
   u00024 : constant Version_32 := 16#34742901#;
   pragma Export (C, u00024, "system__exception_tableB");
   u00025 : constant Version_32 := 16#b1e67ab7#;
   pragma Export (C, u00025, "system__exception_tableS");
   u00026 : constant Version_32 := 16#fc140986#;
   pragma Export (C, u00026, "system__exceptionsS");
   u00027 : constant Version_32 := 16#69416224#;
   pragma Export (C, u00027, "system__exceptions__machineB");
   u00028 : constant Version_32 := 16#59a6462e#;
   pragma Export (C, u00028, "system__exceptions__machineS");
   u00029 : constant Version_32 := 16#aa0563fc#;
   pragma Export (C, u00029, "system__exceptions_debugB");
   u00030 : constant Version_32 := 16#92c2ea31#;
   pragma Export (C, u00030, "system__exceptions_debugS");
   u00031 : constant Version_32 := 16#1253e556#;
   pragma Export (C, u00031, "system__img_intS");
   u00032 : constant Version_32 := 16#01838199#;
   pragma Export (C, u00032, "system__tracebackB");
   u00033 : constant Version_32 := 16#e2576046#;
   pragma Export (C, u00033, "system__tracebackS");
   u00034 : constant Version_32 := 16#1f08c83e#;
   pragma Export (C, u00034, "system__traceback_entriesB");
   u00035 : constant Version_32 := 16#8472457c#;
   pragma Export (C, u00035, "system__traceback_entriesS");
   u00036 : constant Version_32 := 16#53898024#;
   pragma Export (C, u00036, "system__traceback__symbolicB");
   u00037 : constant Version_32 := 16#4f57b9be#;
   pragma Export (C, u00037, "system__traceback__symbolicS");
   u00038 : constant Version_32 := 16#179d7d28#;
   pragma Export (C, u00038, "ada__containersS");
   u00039 : constant Version_32 := 16#701f9d88#;
   pragma Export (C, u00039, "ada__exceptions__tracebackB");
   u00040 : constant Version_32 := 16#bba159a5#;
   pragma Export (C, u00040, "ada__exceptions__tracebackS");
   u00041 : constant Version_32 := 16#edec285f#;
   pragma Export (C, u00041, "interfacesS");
   u00042 : constant Version_32 := 16#fb01eaa4#;
   pragma Export (C, u00042, "interfaces__cB");
   u00043 : constant Version_32 := 16#7300324d#;
   pragma Export (C, u00043, "interfaces__cS");
   u00044 : constant Version_32 := 16#e865e681#;
   pragma Export (C, u00044, "system__bounded_stringsB");
   u00045 : constant Version_32 := 16#d527b704#;
   pragma Export (C, u00045, "system__bounded_stringsS");
   u00046 : constant Version_32 := 16#eb3389a7#;
   pragma Export (C, u00046, "system__crtlS");
   u00047 : constant Version_32 := 16#9e37a39c#;
   pragma Export (C, u00047, "system__dwarf_linesB");
   u00048 : constant Version_32 := 16#053853aa#;
   pragma Export (C, u00048, "system__dwarf_linesS");
   u00049 : constant Version_32 := 16#5b4659fa#;
   pragma Export (C, u00049, "ada__charactersS");
   u00050 : constant Version_32 := 16#ba03ad8f#;
   pragma Export (C, u00050, "ada__characters__handlingB");
   u00051 : constant Version_32 := 16#21df700b#;
   pragma Export (C, u00051, "ada__characters__handlingS");
   u00052 : constant Version_32 := 16#4b7bb96a#;
   pragma Export (C, u00052, "ada__characters__latin_1S");
   u00053 : constant Version_32 := 16#e6d4fa36#;
   pragma Export (C, u00053, "ada__stringsS");
   u00054 : constant Version_32 := 16#24ece25f#;
   pragma Export (C, u00054, "ada__strings__mapsB");
   u00055 : constant Version_32 := 16#ac61938c#;
   pragma Export (C, u00055, "ada__strings__mapsS");
   u00056 : constant Version_32 := 16#9097e839#;
   pragma Export (C, u00056, "system__bit_opsB");
   u00057 : constant Version_32 := 16#0765e3a3#;
   pragma Export (C, u00057, "system__bit_opsS");
   u00058 : constant Version_32 := 16#89dde28e#;
   pragma Export (C, u00058, "system__unsigned_typesS");
   u00059 : constant Version_32 := 16#20c3a773#;
   pragma Export (C, u00059, "ada__strings__maps__constantsS");
   u00060 : constant Version_32 := 16#a0d3d22b#;
   pragma Export (C, u00060, "system__address_imageB");
   u00061 : constant Version_32 := 16#03360b27#;
   pragma Export (C, u00061, "system__address_imageS");
   u00062 : constant Version_32 := 16#d5cc70e4#;
   pragma Export (C, u00062, "system__img_unsS");
   u00063 : constant Version_32 := 16#20ec7aa3#;
   pragma Export (C, u00063, "system__ioB");
   u00064 : constant Version_32 := 16#3c986152#;
   pragma Export (C, u00064, "system__ioS");
   u00065 : constant Version_32 := 16#06253b73#;
   pragma Export (C, u00065, "system__mmapB");
   u00066 : constant Version_32 := 16#c8dac3e3#;
   pragma Export (C, u00066, "system__mmapS");
   u00067 : constant Version_32 := 16#92d882c5#;
   pragma Export (C, u00067, "ada__io_exceptionsS");
   u00068 : constant Version_32 := 16#b124b6b0#;
   pragma Export (C, u00068, "system__mmap__os_interfaceB");
   u00069 : constant Version_32 := 16#060dd44f#;
   pragma Export (C, u00069, "system__mmap__os_interfaceS");
   u00070 : constant Version_32 := 16#8a2d7d24#;
   pragma Export (C, u00070, "system__mmap__unixS");
   u00071 : constant Version_32 := 16#4a925d7c#;
   pragma Export (C, u00071, "system__os_libB");
   u00072 : constant Version_32 := 16#1c53dcbe#;
   pragma Export (C, u00072, "system__os_libS");
   u00073 : constant Version_32 := 16#ec4d5631#;
   pragma Export (C, u00073, "system__case_utilB");
   u00074 : constant Version_32 := 16#9d0f2049#;
   pragma Export (C, u00074, "system__case_utilS");
   u00075 : constant Version_32 := 16#2a8e89ad#;
   pragma Export (C, u00075, "system__stringsB");
   u00076 : constant Version_32 := 16#ee9775cf#;
   pragma Export (C, u00076, "system__stringsS");
   u00077 : constant Version_32 := 16#a2bb689b#;
   pragma Export (C, u00077, "system__object_readerB");
   u00078 : constant Version_32 := 16#f0abf593#;
   pragma Export (C, u00078, "system__object_readerS");
   u00079 : constant Version_32 := 16#7f3a47d4#;
   pragma Export (C, u00079, "system__val_lliS");
   u00080 : constant Version_32 := 16#945fbd74#;
   pragma Export (C, u00080, "system__val_lluS");
   u00081 : constant Version_32 := 16#879d81a3#;
   pragma Export (C, u00081, "system__val_utilB");
   u00082 : constant Version_32 := 16#0e7a20e3#;
   pragma Export (C, u00082, "system__val_utilS");
   u00083 : constant Version_32 := 16#992dbac1#;
   pragma Export (C, u00083, "system__exception_tracesB");
   u00084 : constant Version_32 := 16#a0f69396#;
   pragma Export (C, u00084, "system__exception_tracesS");
   u00085 : constant Version_32 := 16#8c33a517#;
   pragma Export (C, u00085, "system__wch_conB");
   u00086 : constant Version_32 := 16#b9a7b4cf#;
   pragma Export (C, u00086, "system__wch_conS");
   u00087 : constant Version_32 := 16#9721e840#;
   pragma Export (C, u00087, "system__wch_stwB");
   u00088 : constant Version_32 := 16#94b698ce#;
   pragma Export (C, u00088, "system__wch_stwS");
   u00089 : constant Version_32 := 16#1f681dab#;
   pragma Export (C, u00089, "system__wch_cnvB");
   u00090 : constant Version_32 := 16#b6100e3c#;
   pragma Export (C, u00090, "system__wch_cnvS");
   u00091 : constant Version_32 := 16#ece6fdb6#;
   pragma Export (C, u00091, "system__wch_jisB");
   u00092 : constant Version_32 := 16#3660171d#;
   pragma Export (C, u00092, "system__wch_jisS");
   u00093 : constant Version_32 := 16#d8bb58e0#;
   pragma Export (C, u00093, "ada__text_ioB");
   u00094 : constant Version_32 := 16#93922930#;
   pragma Export (C, u00094, "ada__text_ioS");
   u00095 : constant Version_32 := 16#10558b11#;
   pragma Export (C, u00095, "ada__streamsB");
   u00096 : constant Version_32 := 16#67e31212#;
   pragma Export (C, u00096, "ada__streamsS");
   u00097 : constant Version_32 := 16#8c178268#;
   pragma Export (C, u00097, "ada__strings__text_buffersB");
   u00098 : constant Version_32 := 16#0800bb5e#;
   pragma Export (C, u00098, "ada__strings__text_buffersS");
   u00099 : constant Version_32 := 16#cd3494c7#;
   pragma Export (C, u00099, "ada__strings__utf_encodingB");
   u00100 : constant Version_32 := 16#37e3917d#;
   pragma Export (C, u00100, "ada__strings__utf_encodingS");
   u00101 : constant Version_32 := 16#d1d1ed0b#;
   pragma Export (C, u00101, "ada__strings__utf_encoding__wide_stringsB");
   u00102 : constant Version_32 := 16#103ad78c#;
   pragma Export (C, u00102, "ada__strings__utf_encoding__wide_stringsS");
   u00103 : constant Version_32 := 16#c2b98963#;
   pragma Export (C, u00103, "ada__strings__utf_encoding__wide_wide_stringsB");
   u00104 : constant Version_32 := 16#91eda35b#;
   pragma Export (C, u00104, "ada__strings__utf_encoding__wide_wide_stringsS");
   u00105 : constant Version_32 := 16#b3f0dfa6#;
   pragma Export (C, u00105, "ada__tagsB");
   u00106 : constant Version_32 := 16#cb8ac80c#;
   pragma Export (C, u00106, "ada__tagsS");
   u00107 : constant Version_32 := 16#5534feb6#;
   pragma Export (C, u00107, "system__htableB");
   u00108 : constant Version_32 := 16#261825f7#;
   pragma Export (C, u00108, "system__htableS");
   u00109 : constant Version_32 := 16#089f5cd0#;
   pragma Export (C, u00109, "system__string_hashB");
   u00110 : constant Version_32 := 16#84464e89#;
   pragma Export (C, u00110, "system__string_hashS");
   u00111 : constant Version_32 := 16#5fc04ee2#;
   pragma Export (C, u00111, "system__put_imagesB");
   u00112 : constant Version_32 := 16#dff4266b#;
   pragma Export (C, u00112, "system__put_imagesS");
   u00113 : constant Version_32 := 16#e264263f#;
   pragma Export (C, u00113, "ada__strings__text_buffers__utilsB");
   u00114 : constant Version_32 := 16#608bd105#;
   pragma Export (C, u00114, "ada__strings__text_buffers__utilsS");
   u00115 : constant Version_32 := 16#73d2d764#;
   pragma Export (C, u00115, "interfaces__c_streamsB");
   u00116 : constant Version_32 := 16#066a78a0#;
   pragma Export (C, u00116, "interfaces__c_streamsS");
   u00117 : constant Version_32 := 16#30f1a29e#;
   pragma Export (C, u00117, "system__file_ioB");
   u00118 : constant Version_32 := 16#05ab7778#;
   pragma Export (C, u00118, "system__file_ioS");
   u00119 : constant Version_32 := 16#86c56e5a#;
   pragma Export (C, u00119, "ada__finalizationS");
   u00120 : constant Version_32 := 16#95817ed8#;
   pragma Export (C, u00120, "system__finalization_rootB");
   u00121 : constant Version_32 := 16#ed28e58d#;
   pragma Export (C, u00121, "system__finalization_rootS");
   u00122 : constant Version_32 := 16#5f450cb5#;
   pragma Export (C, u00122, "system__file_control_blockS");
   u00123 : constant Version_32 := 16#7a00bb28#;
   pragma Export (C, u00123, "ada__text_io__generic_auxB");
   u00124 : constant Version_32 := 16#48b7189e#;
   pragma Export (C, u00124, "ada__text_io__generic_auxS");
   u00125 : constant Version_32 := 16#ba001e29#;
   pragma Export (C, u00125, "system__img_biuS");
   u00126 : constant Version_32 := 16#f75ed9f7#;
   pragma Export (C, u00126, "system__img_llbS");
   u00127 : constant Version_32 := 16#98c6c945#;
   pragma Export (C, u00127, "system__img_lliS");
   u00128 : constant Version_32 := 16#80af2c85#;
   pragma Export (C, u00128, "system__img_lllbS");
   u00129 : constant Version_32 := 16#f316ccbd#;
   pragma Export (C, u00129, "system__img_llliS");
   u00130 : constant Version_32 := 16#8adb3589#;
   pragma Export (C, u00130, "system__img_lllwS");
   u00131 : constant Version_32 := 16#ab6cacfc#;
   pragma Export (C, u00131, "system__img_llwS");
   u00132 : constant Version_32 := 16#85daa51b#;
   pragma Export (C, u00132, "system__img_wiuS");
   u00133 : constant Version_32 := 16#236b2bc2#;
   pragma Export (C, u00133, "system__val_intS");
   u00134 : constant Version_32 := 16#28959a26#;
   pragma Export (C, u00134, "system__val_unsS");
   u00135 : constant Version_32 := 16#6e5eba19#;
   pragma Export (C, u00135, "system__val_llliS");
   u00136 : constant Version_32 := 16#750441df#;
   pragma Export (C, u00136, "system__val_llluS");
   u00137 : constant Version_32 := 16#f2c63a02#;
   pragma Export (C, u00137, "ada__numericsS");
   u00138 : constant Version_32 := 16#0774605b#;
   pragma Export (C, u00138, "system__random_numbersB");
   u00139 : constant Version_32 := 16#9481a705#;
   pragma Export (C, u00139, "system__random_numbersS");
   u00140 : constant Version_32 := 16#15692802#;
   pragma Export (C, u00140, "system__random_seedB");
   u00141 : constant Version_32 := 16#4e93c571#;
   pragma Export (C, u00141, "system__random_seedS");
   u00142 : constant Version_32 := 16#dbe78fbc#;
   pragma Export (C, u00142, "ada__calendarB");
   u00143 : constant Version_32 := 16#31350a81#;
   pragma Export (C, u00143, "ada__calendarS");
   u00144 : constant Version_32 := 16#51f2d040#;
   pragma Export (C, u00144, "system__os_primitivesB");
   u00145 : constant Version_32 := 16#a527f3eb#;
   pragma Export (C, u00145, "system__os_primitivesS");
   u00146 : constant Version_32 := 16#eca5ecae#;
   pragma Export (C, u00146, "system__memoryB");
   u00147 : constant Version_32 := 16#fba7f029#;
   pragma Export (C, u00147, "system__memoryS");

   --  BEGIN ELABORATION ORDER
   --  ada%s
   --  ada.characters%s
   --  ada.characters.latin_1%s
   --  interfaces%s
   --  system%s
   --  system.img_int%s
   --  system.img_lli%s
   --  system.img_llli%s
   --  system.io%s
   --  system.io%b
   --  system.os_primitives%s
   --  system.os_primitives%b
   --  system.parameters%s
   --  system.parameters%b
   --  system.crtl%s
   --  interfaces.c_streams%s
   --  interfaces.c_streams%b
   --  system.storage_elements%s
   --  system.storage_elements%b
   --  system.stack_checking%s
   --  system.stack_checking%b
   --  system.string_hash%s
   --  system.string_hash%b
   --  system.htable%s
   --  system.htable%b
   --  system.strings%s
   --  system.strings%b
   --  system.traceback_entries%s
   --  system.traceback_entries%b
   --  system.unsigned_types%s
   --  system.img_biu%s
   --  system.img_llb%s
   --  system.img_lllb%s
   --  system.img_lllw%s
   --  system.img_llw%s
   --  system.img_uns%s
   --  system.img_wiu%s
   --  system.wch_con%s
   --  system.wch_con%b
   --  system.wch_jis%s
   --  system.wch_jis%b
   --  system.wch_cnv%s
   --  system.wch_cnv%b
   --  system.traceback%s
   --  system.traceback%b
   --  ada.characters.handling%s
   --  system.case_util%s
   --  system.os_lib%s
   --  system.secondary_stack%s
   --  system.standard_library%s
   --  ada.exceptions%s
   --  system.exceptions_debug%s
   --  system.exceptions_debug%b
   --  system.soft_links%s
   --  system.val_util%s
   --  system.val_util%b
   --  system.val_llu%s
   --  system.val_lli%s
   --  system.wch_stw%s
   --  system.wch_stw%b
   --  ada.exceptions.last_chance_handler%s
   --  ada.exceptions.last_chance_handler%b
   --  ada.exceptions.traceback%s
   --  ada.exceptions.traceback%b
   --  system.address_image%s
   --  system.address_image%b
   --  system.bit_ops%s
   --  system.bit_ops%b
   --  system.bounded_strings%s
   --  system.bounded_strings%b
   --  system.case_util%b
   --  system.exception_table%s
   --  system.exception_table%b
   --  ada.containers%s
   --  ada.io_exceptions%s
   --  ada.strings%s
   --  ada.strings.maps%s
   --  ada.strings.maps%b
   --  ada.strings.maps.constants%s
   --  interfaces.c%s
   --  interfaces.c%b
   --  system.exceptions%s
   --  system.exceptions.machine%s
   --  system.exceptions.machine%b
   --  ada.characters.handling%b
   --  system.exception_traces%s
   --  system.exception_traces%b
   --  system.memory%s
   --  system.memory%b
   --  system.mmap%s
   --  system.mmap.os_interface%s
   --  system.mmap%b
   --  system.mmap.unix%s
   --  system.mmap.os_interface%b
   --  system.object_reader%s
   --  system.object_reader%b
   --  system.dwarf_lines%s
   --  system.dwarf_lines%b
   --  system.os_lib%b
   --  system.secondary_stack%b
   --  system.soft_links.initialize%s
   --  system.soft_links.initialize%b
   --  system.soft_links%b
   --  system.standard_library%b
   --  system.traceback.symbolic%s
   --  system.traceback.symbolic%b
   --  ada.exceptions%b
   --  ada.numerics%s
   --  ada.strings.utf_encoding%s
   --  ada.strings.utf_encoding%b
   --  ada.strings.utf_encoding.wide_strings%s
   --  ada.strings.utf_encoding.wide_strings%b
   --  ada.strings.utf_encoding.wide_wide_strings%s
   --  ada.strings.utf_encoding.wide_wide_strings%b
   --  ada.tags%s
   --  ada.tags%b
   --  ada.strings.text_buffers%s
   --  ada.strings.text_buffers%b
   --  ada.strings.text_buffers.utils%s
   --  ada.strings.text_buffers.utils%b
   --  system.put_images%s
   --  system.put_images%b
   --  ada.streams%s
   --  ada.streams%b
   --  system.file_control_block%s
   --  system.finalization_root%s
   --  system.finalization_root%b
   --  ada.finalization%s
   --  system.file_io%s
   --  system.file_io%b
   --  system.val_lllu%s
   --  system.val_llli%s
   --  system.val_uns%s
   --  system.val_int%s
   --  ada.calendar%s
   --  ada.calendar%b
   --  ada.text_io%s
   --  ada.text_io%b
   --  ada.text_io.generic_aux%s
   --  ada.text_io.generic_aux%b
   --  ada.integer_text_io%s
   --  ada.integer_text_io%b
   --  system.random_seed%s
   --  system.random_seed%b
   --  system.random_numbers%s
   --  system.random_numbers%b
   --  ejercicio6%b
   --  END ELABORATION ORDER

end ada_main;
