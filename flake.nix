{
    description = "Devshell with nix";

    inputs = {
        nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
    };

    outputs =
        { nixpkgs, ... }:
        let
            system = "x86_64-linux";
            pkgs = import nixpkgs { inherit system; };
            runtimeLibs = with pkgs; lib.makeLibraryPath [
                glfw
                libGL
                libX11
                libXcursor
                libXext
                libXrandr
                libXxf86vm
            ];
        in
        {
            devShells.${system}.default = pkgs.mkShell {
                packages = with pkgs; [
                    godot
                    openjdk21
                    jdt-language-server
                    gradle
                ];
                shellHook = ''
                    export LD_LIBRARY_PATH=${runtimeLibs}:$LD_LIBRARY_PATH
                    export _JAVA_AWT_WM_NONREPARENTING=1
                    exec zsh
                '';
            };
        };
}
